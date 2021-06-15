package com.example.memorymanagement.views.login;

import com.example.memorymanagement.views.main.MainView;
import com.example.memorymanagement.views.memorymanagement.MemoryManagementView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "", layout = MainView.class)
@PageTitle("Login")
public class LoginView extends HorizontalLayout {

    public LoginView() {
        addClassName("login-view");
        final var name = new TextField("Your name");
        final var sayHello = new RouterLink("Log in", MemoryManagementView.class);
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
    }
}
