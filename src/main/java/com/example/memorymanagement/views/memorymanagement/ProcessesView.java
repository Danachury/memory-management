package com.example.memorymanagement.views.memorymanagement;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Tag("processes-view")
@JsModule("./views/memorymanagement/processes-view.ts")
public class ProcessesView extends LitTemplate {

    @Id
    private Span apCount;
    @Id
    private Span wpCount;

    @Id("activeProcesses")
    private Grid<Process> apGrid;
    @Id("waitingProcesses")
    private Grid<Process> wpGrid;
    @Id("runProcessesButton")
    private Button wpRunButton;
    @Id("wpClear")
    private Button wpClearButton;

    private final List<Process> activeProcesses;
    private final List<Process> waitingProcesses;

    public ProcessesView() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public ProcessesView(List<Process> activeProcesses, List<Process> waitingProcesses) {
        this.activeProcesses = activeProcesses;
        this.waitingProcesses = waitingProcesses;
        this.apGrid.addColumn(Process::getId).setHeader("Process ID");
        this.apGrid.addColumn(Process::getName).setHeader("Process Name");
        this.apGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.wpGrid.addColumn(Process::getId).setHeader("Process ID");
        this.wpGrid.addColumn(Process::getName).setHeader("Process Name");
        this.wpGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.wpGrid.addSelectionListener(selectionEvent -> this.wpRunButton.setEnabled(!selectionEvent.getAllSelectedItems().isEmpty()));
        this.wpRunButton.setEnabled(false);
        this.wpClearButton.addClickListener(event -> clearWaitingProcesses());
    }

    public void addToActiveProcess(List<Process> processes) {
        final var filteredProcesses = processes
            .stream()
            .filter(process -> !this.activeProcesses.contains(process))
            .collect(Collectors.toList());
        this.activeProcesses.addAll(filteredProcesses);
        this.updateActiveUI();
    }

    public void addToWaitingProcess(List<Process> processes) {
        final var filteredProcesses = processes
            .stream()
            .filter(process -> !this.waitingProcesses.contains(process))
            .collect(Collectors.toList());
        this.waitingProcesses.addAll(filteredProcesses);
        this.updateWaitingUI();
    }

    public void clearActiveProcesses() {
        this.activeProcesses.clear();
        updateActiveUI();
    }

    public void clearWaitingProcesses() {
        this.waitingProcesses.clear();
        this.updateWaitingUI();
    }

    public void removeWaitingProcesses(List<Process> processes) {
        final var filteredProcesses = this.waitingProcesses
            .stream()
            .filter(process -> !processes.contains(process))
            .collect(Collectors.toList());
        this.waitingProcesses.clear();
        this.waitingProcesses.addAll(filteredProcesses);
        this.updateWaitingUI();
    }

    public List<Process> getProcessesSelected() {
        return new ArrayList<>(this.wpGrid.getSelectedItems());
    }

    public void updateWaitingUI() {
        this.wpGrid.setItems(this.waitingProcesses);
        this.wpCount.setText(String.valueOf(this.waitingProcesses.size()));
    }

    public void updateActiveUI() {
        this.apGrid.setItems(this.activeProcesses);
        this.apCount.setText(String.valueOf(this.activeProcesses.size()));
    }
}
