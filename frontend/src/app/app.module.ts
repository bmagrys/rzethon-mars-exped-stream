import {NgModule, ErrorHandler} from "@angular/core";
import {IonicApp, IonicModule, IonicErrorHandler} from "ionic-angular";
import {MyApp} from "./app.component";
import {AboutPage} from "../pages/about/about";
import {DevicesComponent} from "../pages/devices/devices.component";
import {TabsPage} from "../pages/tabs/tabs";
import {DeviceDetailComponent} from "../pages/device-detail/device-detail.component";
import {DeviceService} from "../pages/devices/devices.service";
import {HttpModule} from "@angular/http";
@NgModule({
  declarations: [
    MyApp,
    AboutPage,
    DeviceDetailComponent,
    DevicesComponent,
    TabsPage,
  ],
  imports: [
    HttpModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    AboutPage,
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
