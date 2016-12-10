import {deserialize, serialize} from 'cerialize';

export class DevDetails {
  @deserialize public name: string;
  @deserialize public positionX: number;
  @deserialize public positionY: number;
  @deserialize public temperature: number;
  @deserialize public humidity: number;
  @deserialize public lightIntensity: number;
  @deserialize public vibrations: number;
  @deserialize public gasConcentration: number;
}
