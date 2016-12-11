import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/toPromise";

@Injectable()
export class DeviceService {

  private devicesUrl = 'http://192.168.137.1:5001/devices';

  constructor(private http: Http) {
  }

  getDeviceNames(): Promise<String[]> {
    return this.http.get(this.devicesUrl)
      .toPromise()
      .then(response => response.json() as String[])
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}

