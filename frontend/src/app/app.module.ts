import {NgModule, ErrorHandler} from "@angular/core";
import {IonicApp, IonicModule, IonicErrorHandler} from "ionic-angular";
import {MyApp} from "./app.component";
import {MapPage} from "../pages/about/map";
import {DevicesComponent} from "../pages/devices/devices.component";
import {TabsPage} from "../pages/tabs/tabs";
import {DeviceDetailComponent} from "../pages/device-detail/device-detail.component";
import {DeviceService} from "../pages/devices/devices.service";
// import {Routes, RouterModule} from "@angular/router";

// const routes: Routes = [
//   { path: '', redirectTo: '/devices', pathMatch: 'full' },
//   { path: 'detail/:name', component: DeviceDetailComponent },
//   { path: 'devices',     component: DevicesComponent }
// ];

@NgModule({
  declarations: [
    MyApp,
    MapPage,
    DeviceDetailComponent,
    DevicesComponent,
    TabsPage,
  ],
  imports: [
    // RouterModule.forRoot(routes),
    IonicModule.forRoot(MyApp)
  ],
  // exports: [
  //   RouterModule
  // ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    MapPage,
    DeviceDetailComponent,
    DevicesComponent,
    TabsPage
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler},
    DeviceService
  ]
})
export class AppModule {
}
