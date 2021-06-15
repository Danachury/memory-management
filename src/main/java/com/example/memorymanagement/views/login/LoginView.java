package com.example.memorymanagement.views.login;

import com.example.memorymanagement.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
@PageTitle("Login")
public class LoginView extends HorizontalLayout {

    private final TextField name;

    public LoginView() {
        addClassName("login-view");
        name = new TextField("Your name");
        final var sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
    }
}
