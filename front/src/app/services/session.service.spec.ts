import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import {SessionInformation} from "../interfaces/sessionInformation.interface";

describe('SessionService', () => {
  let service: SessionService;
  let mockUser: SessionInformation = {
    admin: false,
    firstName: "",
    id: 0,
    lastName: "",
    token: "",
    type: "",
    username: ""
  };

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login the user', (done) => {
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(true);
      done();
    });

    service.logIn(mockUser);
    expect(service.sessionInformation).toEqual(mockUser);
  });

  it('should logout the user', (done) => {
    service.logIn(mockUser); // first log in

    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });

    service.logOut();
    expect(service.sessionInformation).toBeUndefined();
  });
});
