import {Injectable} from "@angular/core";
import {DEVICE_NAMES} from "./mock-devices";
import {Device} from "../device-detail/device-info";

@Injectable()
export class DeviceService {

  getDeviceNames(): Promise<String[]> {
    return Promise.resolve(DEVICE_NAMES);
  }
}

