import {IEvent} from "./i-event";
import {ICoordinates} from "./i-coordinates";

export interface IUser {

  phone_number?: string;
  first_name?: string;
  last_name?: string;
  email: string;
  about_me?: string;
  profile_img?: string;
  is_admin?: Boolean;
  birthday?: Date;
  gender?: string;
  favorite_foods?: string[];
  address?: string;
  country?: string;
  favorite_restaurants?: string[];
  restaurants?: string[];
  tmp_password?: string;
  tmp_time?: Date;
  accessToken?: string;
  creation_date?: Date;
  last_modified?: Date;
  events?: IEvent[];
  coordinates?: ICoordinates;

}
