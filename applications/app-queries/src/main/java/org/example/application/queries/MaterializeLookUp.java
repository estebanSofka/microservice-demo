package org.example.application.queries;

import org.example.application.queries.adapter.repo.AccountModeView;
import org.example.application.queries.adapter.repo.AccountRepository;
import org.example.application.queries.adapter.repo.TransactionModelView;
import org.example.domain.events.AccountCreated;
import org.example.domain.events.AccountDeactivated;
import org.example.domain.events.TransactionAdded;
import org.example.generic.DelegateService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Component
public class MaterializeLookUp {
    private final Map<String, Flux<DelegateService>> business = new HashMap<>();

    public MaterializeLookUp(ApplicationContext context, AccountRepository repository) {
        business.put("org.example.AccountCreated", Flux.just(input -> {
            var event = (AccountCreated) input;
            var document = new AccountModeView();
            document.setId(event.aggregateRootId());
            document.setName(event.getName().value());
            document.setUserId(event.getUserId().value());
            document.setActive(Boolean.TRUE);
            return repository.save(document).then();
        }));

        business.put("org.example.AccountDeactivated", Flux.just(input -> {
            var event = (AccountDeactivated) input;
            return repository.findById(event.aggregateRootId()).flatMap(doc -> {
                doc.setActive(Boolean.FALSE);
                return repository.save(doc).then();
            });
        }));

        business.put("org.example.TransactionAdded", Flux.just(input -> {
            var event = (TransactionAdded) input;
            return repository.findById(event.aggregateRootId()).flatMap(doc -> {

                var transactionModelView = new TransactionModelView();
                transactionModelView.setTransactionDate(event.getTransactionDate().value());
                transactionModelView.setTransactionType(event.getTransactionType().value());
                transactionModelView.setAmount(event.getAmount().value());
                transactionModelView.setId(event.getId().value());
                transactionModelView.setName(event.getName().value());
                var trans = doc.getTransactionModelViews();
                trans.put(event.getId().value(), transactionModelView);

                doc.setTransactionModelViews(trans);
                switch (event.getTransactionType().value()) {
                    case "DEPOSIT":
                    case "ROI":
                        doc.setBalance(doc.getBalance() + event.getAmount().value());
                        break;
                    case "WITHDRAWAL":
                        doc.setBalance(doc.getBalance() - event.getAmount().value());
                        break;
                    default:
                        break;
                }
                return repository.save(doc).then();
            });
        }));
    }


    public Flux<DelegateService> get(String eventType) {
        return business.getOrDefault(eventType, Flux.empty());
    }
}
