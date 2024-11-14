package com.glass.project;

import java.util.List;

public class Production {
    String leftSide;
    List<String> rightSide;
    
    public Production(String leftSide, List<String> rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(leftSide);
        result.append(" -> ");
        for (int i = 0; i < rightSide.size(); i++) {
            if (i == 0) {
                result.append(rightSide.get(i));
            } else {
                result.append(String.format(" %s", rightSide.get(i)));
            }
        }
        return result.toString();
    }
}
