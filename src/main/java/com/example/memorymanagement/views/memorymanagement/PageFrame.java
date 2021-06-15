package com.example.memorymanagement.views.memorymanagement;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.memorymanagement.views.memorymanagement.Memory.MAX_SIZE;

@Getter
@Setter
@Tag("page-frame")
public class PageFrame extends HorizontalLayout {

    private final Button terminateButton = new Button("Terminate");
    private final Span count = new Span("0");
    private final Grid<Process> grid = new Grid<>();

    private Process[] processes = new Process[MAX_SIZE];

    public PageFrame() {
        this.grid.addColumn(Process::getId).setHeader("Process ID");
        this.grid.addColumn(Process::getName).setHeader("Process Name");
        this.grid.addClassNames("bg-contrast-5", "rounded-l");
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addSelectionListener(selectionEvent ->
            this.terminateButton
                .setEnabled(!selectionEvent.getAllSelectedItems().isEmpty())
        );
        final var h4 = new H4("PAGINATION FRAME");
        h4.add(this.count);
        h4.addClassNames("flex", "justify-between");
        this.terminateButton.setEnabled(false);
        this.terminateButton.addClickListener(event -> {
            this.processes = Arrays.stream(this.processes)
                .filter(process -> !this.grid.getSelectedItems().contains(process))
                .collect(Collectors.toList())
                .toArray(new Process[MAX_SIZE]);
            this.updateUI();
        });
        this.add(h4);
        this.add(this.terminateButton);
        this.add(this.grid);
    }

    public boolean addProcess(List<Process> processes) {
        final var emptyMemoryDirs = this.hasAvailableSpace();
        if (processes.size() > emptyMemoryDirs)
            return false;
        for (var newProcess : processes) {
            for (var j = 0; j < MAX_SIZE; j++) {
                if (Arrays.asList((this.processes)).contains(newProcess))
                    break;
                if (Objects.isNull(this.processes[j]))
                    this.processes[j] = newProcess;
            }
        }
        this.updateUI();
        return true;
    }

    public List<Process> getItems() {
        return Arrays.stream(this.processes).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public long hasAvailableSpace() {
        return Arrays.stream(this.processes).filter(Objects::isNull).count();
    }

    public void clear() {
        this.processes = new Process[MAX_SIZE];
        this.count.setText("0");
        this.grid.setItems(this.getItems());
    }

    private void updateUI() {
        final var items = this.getItems();
        this.processes = items.toArray(new Process[MAX_SIZE]);
        this.count.setText(String.valueOf(items.size()));
        this.grid.setItems(items);
    }
}
