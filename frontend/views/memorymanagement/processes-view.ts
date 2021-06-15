import { customElement, html, LitElement } from "lit-element";

@customElement('processes-view')
export class ProcessesView extends LitElement {

  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  protected render(): unknown {
    return html`
       <div class="flex flex-col">
          <h4 class="flex justify-between">
             <span>ACTIVE PROCESSES</span>
             <span id="apCount">0</span>
          </h4>
          <vaadin-grid id="activeProcesses" class="flex bg-contrast-5 rounded-l"></vaadin-grid>
       </div>
       <div class="flex flex-col">
          <h4 class="flex justify-between">
             <span>WAITING PROCESSES</span>
             <span id="wpCount">0</span>
          </h4>
          <div class="flex">
             <vaadin-button id="runProcessesButton">Run</vaadin-button>
             <vaadin-button id="wpClear" class="mx-s">Clear List</vaadin-button>
          </div>
          <vaadin-grid id="waitingProcesses" class="flex bg-contrast-5 rounded-l"></vaadin-grid>
       </div>
    `;
  }
}
