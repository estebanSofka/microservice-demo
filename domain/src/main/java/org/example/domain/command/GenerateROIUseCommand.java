package org.example.domain.command;

import org.example.domain.value.ROIRate;
import org.example.generic.domain.Command;

public class GenerateROIUseCommand extends Command {
    private final ROIRate roiRate;

    public GenerateROIUseCommand(ROIRate roiRate) {
        this.roiRate = roiRate;
    }

    public ROIRate getRoiRate() {
        return roiRate;
    }
}
