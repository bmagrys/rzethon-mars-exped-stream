import {Component} from "@angular/core";
import {Deserialize, Serialize} from "cerialize";
import {NavController} from "ionic-angular";
import {DevDetails} from "./dev-details";
declare var EventSource: any;

@Component({
  selector: 'page-devices',
  templateUrl: 'devices.html'
})
export class DevicesPage {
  // someStrings: string[] = [];
  device: DevDetails = {
    name: 'sampleName',
    positionX: 1,
    positionY: 1,
    temperature: 2,
    humidity: 3,
    lightIntensity: 4,
    vibrations: 5,
    gasConcentration: 6
  };

  constructor(public navCtrl: NavController) {
  }

  ngOnInit() {
    console.log("Hello from ngOnInit!");
    let source = new EventSource('http://localhost:5001/events');
    source.addEventListener('message',
      aString => {
        // this.someStrings.push(aString.data), false
        const json = Serialize(this.device);
        // console.log(aString.data);
        var obj = JSON.parse(aString.data);
        console.log(obj)
        this.device = Deserialize(obj, DevDetails)
      }
    );
  }

}
