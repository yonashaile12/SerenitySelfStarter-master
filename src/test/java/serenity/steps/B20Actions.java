package serenity.steps;

import net.thucydides.core.annotations.Step;

public class B20Actions {

    String actor ;

    @Step("#actor This is where I prepare all stuff before taking action")
    public void preparedSomething(){
        System.out.println("preparing cool stuff");
    }
    @Step("#actor Taking some awsome action here")
    public void takenAction(){
        System.out.println("taking some action");
    }

    @Step("#actor Eventually expecting tremendous result")
    public void expectSomeResult(){
        System.out.println("expecting some result");
    }
}
