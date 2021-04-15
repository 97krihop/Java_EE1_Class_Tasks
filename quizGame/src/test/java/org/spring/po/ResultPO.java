package org.spring.po;

import org.openqa.selenium.By;
import org.tsdes.misc.testutils.selenium.PageObject;

public class ResultPO extends PageObject {

    public ResultPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Result");
    }

    public boolean haveWon(){
        return getDriver().findElements(By.id("wonId")).size() > 0;
    }

    public boolean haveLost(){
        return getDriver().findElements(By.id("lostId")).size() > 0;
    }
}

