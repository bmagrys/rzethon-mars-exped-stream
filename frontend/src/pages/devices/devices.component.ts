import {Component} from "@angular/core";
import {NavController} from "ionic-angular";
import {DeviceService} from "./devices.service";
import {DeviceDetailComponent} from "../device-detail/device-detail.component";

@Component({
  selector: 'page-devices',
  templateUrl: 'devices.component.html',
  providers: [DeviceService]
})
export class DevicesComponent {

  deviceNames: String[];

  constructor(private deviceService: DeviceService,
              public navCtrl: NavController) {
  }

  ngOnInit() {
    this.getDeviceNames();
  }

  getDeviceNames(): void {
    this.deviceService.getDeviceNames().then(deviceNames => this.deviceNames = deviceNames);
  }

  onSelect(name: String): void {
    this.navCtrl.push(DeviceDetailComponent, {deviceName: name});
  }

}
