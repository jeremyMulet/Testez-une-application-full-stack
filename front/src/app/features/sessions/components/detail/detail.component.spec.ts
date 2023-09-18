import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {RouterTestingModule,} from '@angular/router/testing';
import {expect} from '@jest/globals';
import {SessionService} from '../../../../services/session.service';

import {DetailComponent} from './detail.component';
import {of} from "rxjs";
import {SessionApiService} from "../../services/session-api.service";
import {Session} from "../../interfaces/session.interface";
import {ActivatedRoute, Router} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {TeacherService} from "../../../../services/teacher.service";
import {Teacher} from "../../../../interfaces/teacher.interface";


describe('DetailComponent', () => {

  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
      username: '',
      firstName: '',
      lastName: ''
    }
  };
  let mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('1'),
      },
    },
  };
  let mockRouter: jest.Mocked<Router>;
  let mockMatSnackBar: jest.Mocked<MatSnackBar>;
  let mockSessionApiService: jest.Mocked<SessionApiService>;
  let mockSession: jest.Mocked<Session>;
  let mockTeacherService: jest.Mocked<TeacherService>;
  let mockTeacher: jest.Mocked<Teacher>;

  beforeEach(async () => {

    mockSession = {
      id: 2,
      name: "Session yoga",
      description: "description de la session",
      date: new Date(),
      teacher_id: 2,
      users: [0, 3, 6],
    } as unknown as jest.Mocked<Session>;

    mockTeacher = {
      id: 2,
      lastName: 'Darc',
      firstName: 'Jeanne'
    } as unknown as jest.Mocked<Teacher>;

    mockMatSnackBar = {
      open: jest.fn()
    } as unknown as jest.Mocked<MatSnackBar>;

    mockSessionApiService = {
      delete: jest.fn(),
      participate: jest.fn(),
      unParticipate: jest.fn(),
      detail: jest.fn().mockReturnValue(of(mockSession))
    } as unknown as jest.Mocked<SessionApiService>;

    mockRouter = {
      navigate: jest.fn()
    } as unknown as jest.Mocked<Router>;

    mockTeacherService = {
      detail: jest.fn().mockReturnValue(of(mockTeacher))
    } as unknown as jest.Mocked<TeacherService>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatIconModule,
        MatCardModule,
      ],
      declarations: [DetailComponent],
      providers: [
        {provide: MatSnackBar, useValue: mockMatSnackBar},
        {provide: Router, useValue: mockRouter},
        {provide: SessionApiService, useValue: mockSessionApiService},
        {provide: SessionService, useValue: mockSessionService},
        {provide: ActivatedRoute, useValue: mockActivatedRoute},
        {provide: TeacherService, useValue: mockTeacherService}
      ],
    }).compileComponents();


    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    component.sessionId = "2";
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch session', () => {
    const spyApi = jest.spyOn(mockSessionApiService, 'detail').mockReturnValue(of(mockSession));
    const spyTeacherService = jest.spyOn(mockTeacherService, 'detail').mockReturnValue(of(mockTeacher));

    component.ngOnInit();

    expect(spyApi).toHaveBeenCalledWith("2");
    expect(component.session).toBe(mockSession);
    expect(component.isParticipate).toBe(mockSession.users.some(u => u === mockSessionService.sessionInformation!.id));
    expect(spyTeacherService).toHaveBeenCalledWith(mockSession.teacher_id.toString());
    expect(component.teacher).toBe(mockTeacher);
  });

  it('should call window.history.back when back() is called', () => {
    const backSpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(backSpy).toHaveBeenCalled();
  });

  it('should delete session', () => {
    const backSpy = jest.spyOn(mockSessionApiService, 'delete').mockReturnValue(of(null));
    component.delete()
    expect(backSpy).toHaveBeenCalledWith('2');
    expect(mockMatSnackBar.open).toHaveBeenCalledWith('Session deleted !', 'Close', {duration: 3000});
    expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should fetch session after unParticipate', () => {
    const sessionApiService = TestBed.inject(SessionApiService);
    const unParticipateSpy = jest.spyOn(sessionApiService, 'unParticipate').mockReturnValue(of(undefined));

    component.unParticipate();
    expect(unParticipateSpy).toHaveBeenCalledWith("2", "1");
  });

  it('should fetch session after Participate', () => {
    const sessionApiService = TestBed.inject(SessionApiService);
    const participateSpy = jest.spyOn(sessionApiService, 'participate').mockReturnValue(of(undefined));

    component.participate();
    expect(participateSpy).toHaveBeenCalledWith("2", "1");
  });

});

