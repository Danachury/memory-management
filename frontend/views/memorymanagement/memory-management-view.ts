import { customElement, html, LitElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';
// @ts-ignore
import { applyTheme } from 'themes/theme-generated.js';


@customElement('memory-management-view')
export class MemoryManagementView extends LitElement {
  connectedCallback() {
    super.connectedCallback();
    // Apply the theme manually because of https://github.com/vaadin/flow/issues/11160
    applyTheme(this.renderRoot);
  }

  render() {
    return html`
       <main class="max-w-screen-x-lg pb-l px-l">
          <vaadin-horizontal-layout class="items-center justify-between">
             <vaadin-vertical-layout>
                <h2 class="mb-0 mt-xl text-3xl">Memory Pagination</h2>
                <p class="mb-l mt-0 text-secondary">
                   Memory Pagination Example
                   <br>
                   <span class="mb-xl mt-0 text-s font-semibold capitalize" id="osName"></span>
                   <span class="mb-xl mt-0 text-s font-extralight" id="ramSize"></span>
                </p>
             </vaadin-vertical-layout>
          </vaadin-horizontal-layout>
          <hr>
          <vaadin-form-layout class="flex gap-l flex-wrap mb-xl">
             <vaadin-select label="Process Name" id="processesName"></vaadin-select>
             <vaadin-select label="Quantity" id="processesSize"></vaadin-select>
             <span>
                <vaadin-button id="startProcessButton">Start Process</vaadin-button>
             </span>
          </vaadin-form-layout>
          <vaadin-horizontal-layout class="flex gap-m">
             <slot></slot>
          </vaadin-horizontal-layout>
       </main>
    `;
  }
}
