import {deserialize} from "cerialize";

export class Device {
  @deserialize public name: string;
  @deserialize public positionX: number;
  @deserialize public positionY: number;
  @deserialize public temperature: number;
  @deserialize public humidity: number;
  @deserialize public lightIntensity: number;
  @deserialize public vibrations: number;
  @deserialize public gasConcentration: number;
}
