package com.example.memorymanagement.views.main;

import com.example.memorymanagement.views.login.LoginView;
import com.example.memorymanagement.views.memorymanagement.MemoryManagementView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Memory Management", shortName = "Memory Management", enableInstallPrompt = false)
@Theme(themeFolder = "memorymanagement", variant = Lumo.DARK)
public class MainView extends AppLayout {

    private final Tabs menu;
    private final Avatar avatar;
    private H1 viewTitle;

    public MainView() {
        this.avatar = new Avatar();
        this.setPrimarySection(Section.DRAWER);
        this.addToNavbar(true, createHeaderContent());
        this.menu = createMenu();
        this.addToDrawer(this.createDrawerContent(this.menu));
    }

    private Component createHeaderContent() {
        final var layout = new HorizontalLayout();
        layout.setClassName("sidemenu-header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        this.viewTitle = new H1();
        layout.add(this.viewTitle);
        layout.add(this.avatar);
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        final var layout = new VerticalLayout();
        layout.setClassName("sidemenu-menu");
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        final var logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "Memory Management logo"));
        logoLayout.add(new H1("Memory Management"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final var tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(this.createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
            createTab("Login", LoginView.class),
            createTab("Memory Management", MemoryManagementView.class)
        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final var tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        this.getTabForComponent(this.getContent()).ifPresent(this.menu::setSelectedTab);
        this.viewTitle.setText(this.getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return this.menu
            .getChildren()
            .filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
            .findFirst()
            .map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        final var title = this.getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
