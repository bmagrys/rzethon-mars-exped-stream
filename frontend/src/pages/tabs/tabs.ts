import { Component } from '@angular/core';

import { DevicesComponent } from '../devices/devices.component';
import { AboutPage } from '../about/about';

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {
  // this tells the tabs component which Pages
  // should be each tab's root Page
  tab1Root: any = DevicesComponent;
  tab2Root: any = AboutPage;

  constructor() {

  }
}
