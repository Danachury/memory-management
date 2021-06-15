package com.example.memorymanagement.views.memorymanagement;

import com.example.memorymanagement.views.main.MainView;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.memorymanagement.views.memorymanagement.Memory.MAX_SIZE;

@Route(value = "memory", layout = MainView.class)
@PageTitle("Memory Management")
@Tag("memory-management-view")
@JsModule("./views/memorymanagement/memory-management-view.ts")
public class MemoryManagementView extends LitTemplate implements HasComponents, HasStyle {

    @Id
    private Select<Character> processesName;
    @Id
    private Select<Short> processesSize;
    @Id
    private Button startProcessButton;
    @Id
    private Button clearButton;

    private final PageFrame pageFrame = new PageFrame();
    private final ProcessesView processesView = new ProcessesView();

    public MemoryManagementView() {
        this.addClassNames("memory-management-view", "flex", "flex-col", "h-full");
        this.processesName.setItems('A', 'B', 'C', 'D', 'E', 'F');
        final var sizeItems = IntStream
            .rangeClosed(1, MAX_SIZE)
            .boxed()
            .map(Integer::shortValue)
            .collect(Collectors.toList());
        this.processesSize.setItems(sizeItems);

        this.init();
    }

    private void init() {
        this.add(this.pageFrame);
        this.add(this.processesView);
        this.startProcessButton.addClickListener(event -> {
            final var processes = IntStream
                .range(0, this.processesSize.getValue())
                .mapToObj(i -> new Process(this.processesName.getValue()))
                .collect(Collectors.toList());
            this.startProcess(processes);
            this.processesName.clear();
            this.processesSize.clear();
        });
        this.startProcessButton.setEnabled(false);
        this.clearButton.addClickListener(event -> this.onClearPageFrame());
        this.processesName.addValueChangeListener(event -> this.startProcessButton.setEnabled(this.processesSize.getOptionalValue().isPresent()));
        this.processesSize.addValueChangeListener(event -> this.startProcessButton.setEnabled(this.processesName.getOptionalValue().isPresent()));
        this.processesView.getWpRunButton().addClickListener(event -> startWaitingProcess());
    }

    private void startWaitingProcess() {
        final var processesSelected = this.processesView.getProcessesSelected();
        final var updated = this.startProcess(processesSelected);
        if (updated)
            this.processesView.removeWaitingProcesses(processesSelected);
    }

    private boolean startProcess(List<Process> processes) {
        final var procIds = processes.stream().map(Process::getId).collect(Collectors.joining(","));
        if (!this.addToPageFrame(processes)) {
            this.processesView.addToWaitingProcess(processes);
            Notification.show(
                "Processes [" + procIds + "] Added to waiting list",
                3000,
                Notification.Position.TOP_END
            );
            return false;
        }
        this.processesView.addToActiveProcess(processes);
        Notification.show("Processes [" + procIds + "] Started successfully.", 3000, Notification.Position.TOP_END);
        return true;
    }

    private boolean addToPageFrame(List<Process> processes) {
        return this.pageFrame.addProcess(processes);
    }

    private void onClearPageFrame() {
        this.pageFrame.clear();
        this.processesView.clearActiveProcesses();
        Notification.show("List of Processes Cleaned.", 3000, Notification.Position.TOP_END);
    }
}
