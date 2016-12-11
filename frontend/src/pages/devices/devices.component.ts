import {Component} from "@angular/core";
import {NavController} from "ionic-angular";
import {Device} from "../device-detail/device-info";
import {DeviceService} from "./devices.service";
import {DeviceDetailComponent} from "../device-detail/device-detail.component";

@Component({
  selector: 'page-devices',
  templateUrl: 'devices.component.html',
  providers: [DeviceService]
})
export class DevicesComponent {

  deviceNames: String[];

  constructor(//private router: Router,
              private deviceService: DeviceService,
              public navCtrl: NavController) {
  }

  ngOnInit() {
    this.getDeviceNames();
  }

  getDeviceNames(): void {
    this.deviceService.getDeviceNames().then(deviceNames => this.deviceNames = deviceNames);
  }

  // getDeviceInfo(name: String) {
  //   if (this.source != null)
  //     this.source.removeEventListener('message', this.updateDeviceInfo, false);
  //   this.source = new EventSource('http://192.168.137.1:5001/event?name=' + name);
  //   console.log('getDeviceInfo()');
  //   this.source.addEventListener('message', this.updateDeviceInfo, false);
  // }

  onSelect(name: String): void {
    this.navCtrl.push(DeviceDetailComponent, {deviceName: name});
  }

  // pushPage() {
  //   this.navCtrl.push(DeviceDetailComponent);
  // }

  // gotoDetail(): void {
  //   this.router.navigate(['/detail', this.selectedDevice.name])
  // }
}
