import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import {User} from "../interfaces/user.interface";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get user by his id', () => {
    const mockUser: User = {
      admin: false, createdAt: new Date(), email: "", firstName: "", lastName: "", password: "", id: 4
    };
    const userId = '4'

    service.getById(userId).subscribe( user => {
      expect(user).toEqual(mockUser);
    });

    const request = httpMock.expectOne(`api/user/${userId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockUser);
  });

  it('should delete user by id', () => {
    const userId = '4'

    service.delete(userId).subscribe(response => {
      expect(response).toBe(null);
    });

    const request = httpMock.expectOne(`api/user/${userId}`);
    expect(request.request.method).toBe('DELETE');
    request.flush(null);
  });

});
