import {Component} from "@angular/core";
import {Device} from "./device-info";
import {Deserialize} from "cerialize";
import {DeviceService} from "../devices/devices.service";
import {NavParams} from "ionic-angular";
declare var EventSource: any;

@Component({
  selector: 'device-detail',
  templateUrl: 'device-detail.component.html',
  providers: [DeviceService]
})
export class DeviceDetailComponent {
  device: Device;
  deviceName: String;
  source;

  constructor(public params: NavParams) {
    this.deviceName = params.get("deviceName")
  }

  updateDeviceInfo = aString => {
    var obj = JSON.parse(aString.data);
    console.log(obj);
    var deserialized: Device = Deserialize(obj, Device);
    if (deserialized.name != "")
      this.device = Deserialize(obj, Device)
  };

  ngOnInit() {
    console.log(this.deviceName);
    this.getDeviceInfo(this.deviceName);
  }


  ionViewDidLoad() {
    console.log("I'm alive!");
  }

  ionViewWillLeave() {
    if (this.source != null)
      this.source.removeEventListener('message', this.updateDeviceInfo, false);
  }

  getDeviceInfo(name: String) {
    // if (this.source != null)
    //   this.source.removeEventListener('message', this.updateDeviceInfo, false);
    this.source = new EventSource('http://192.168.137.1:5001/event?name=' + name);
    console.log('getDeviceInfo()');
    this.source.addEventListener('message', this.updateDeviceInfo, false);
  }

}
