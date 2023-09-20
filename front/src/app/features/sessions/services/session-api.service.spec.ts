import {TestBed} from '@angular/core/testing';
import {expect} from '@jest/globals';

import {SessionApiService} from './session-api.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Session} from "../interfaces/session.interface";

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;
  let mockSession: jest.Mocked<Session> = {
    id: 23,
    date: new Date(),
    description: "",
    name: "",
    teacher_id: 0,
    users: []
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],

    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get session detail', () => {
    service.detail('1').subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });

  it('should delete session', () => {
    service.delete('1').subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should create session', () => {
    service.create(mockSession).subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    req.flush(mockSession);
  });

  it('should update session', () => {
    service.update('1', mockSession).subscribe(session => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockSession);
  });

  it('should participate to the session', () => {
    const userId = '123';
    const sessionId = '1';

    service.participate(sessionId, userId).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`api/session/${sessionId}/participate/${userId}`);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should unParticipate the session', () => {
    const userId = '123';
    const sessionId = '1';

    service.unParticipate(sessionId, userId).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`api/session/${sessionId}/participate/${userId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });


});
