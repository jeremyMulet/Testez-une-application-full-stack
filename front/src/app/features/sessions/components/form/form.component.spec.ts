import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {RouterTestingModule} from '@angular/router/testing';
import {expect} from '@jest/globals';
import {SessionService} from 'src/app/services/session.service';
import {SessionApiService} from '../../services/session-api.service';

import {FormComponent} from './form.component';
import {ActivatedRoute, Router} from "@angular/router";
import {of} from "rxjs";
import {Session} from "../../interfaces/session.interface";

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let mockRouter = {
    url: 'update',
    navigate: jest.fn(),
  } as unknown as jest.Mocked<Router>;
  let mockApiService: jest.Mocked<SessionApiService>;
  let mockSession: jest.Mocked<Session>;
  let mockMatSnackBar: jest.Mocked<MatSnackBar>;
  let mockSessionService = {
    sessionInformation: {
      admin: true
    }
  };
  let mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('1'),
      },
    },
  };

  beforeEach(async () => {

    mockSession = {
      id: 2,
      name: "Session yoga",
      description: "description de la session"
    } as unknown as jest.Mocked<Session>;

    mockApiService = {
      detail: jest.fn().mockReturnValue(of(mockSession)),
      create: jest.fn().mockReturnValue(of(mockSession)),
      update: jest.fn().mockReturnValue(of(mockSession))
    } as unknown as jest.Mocked<SessionApiService>;

    mockMatSnackBar = {
      open: jest.fn()
    } as unknown as jest.Mocked<MatSnackBar>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule
      ],
      providers: [
        {provide: SessionService, useValue: mockSessionService},
        {provide: Router, useValue: mockRouter},
        {provide: ActivatedRoute, useValue: mockActivatedRoute},
        {provide: SessionApiService, useValue: mockApiService},
        {provide: MatSnackBar, useValue: mockMatSnackBar},
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.onUpdate = false;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to /sessions', () => {
    mockSessionService.sessionInformation.admin = false;
    const routerSpy = jest.spyOn(mockRouter, 'navigate');

    component.ngOnInit();
    expect(routerSpy).toHaveBeenCalledWith(['/sessions'])
  });

  it('should init form on update case', () => {
    const spyApi = jest.spyOn(mockApiService, 'detail').mockReturnValue(of(mockSession))

    component.ngOnInit();
    expect(component.onUpdate).toBeTruthy();
    expect(spyApi).toHaveBeenCalledWith('1');
  });

  it('should init form on normal case', () => {
    Object.defineProperty(mockRouter, 'url', {value: 'something', writable: true});

    component.ngOnInit();
    expect(component.onUpdate).toBeFalsy();
  });

  it('should submit create form', () => {
    const apiSpy = jest.spyOn(mockApiService, 'create');
    const routerSpy = jest.spyOn(mockRouter, 'navigate');
    const snackBarSpy = jest.spyOn(mockMatSnackBar, 'open');

    component.submit();
    expect(apiSpy).toHaveBeenCalled();
    expect(snackBarSpy).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 })
    expect(routerSpy).toHaveBeenCalledWith(['sessions']);
  });

  it('should submit update form', () => {
    component.onUpdate = true;
    const apiSpy = jest.spyOn(mockApiService, 'update');
    const routerSpy = jest.spyOn(mockRouter, 'navigate');
    const snackBarSpy = jest.spyOn(mockMatSnackBar, 'open');

    component.submit();
    expect(apiSpy).toHaveBeenCalled();
    expect(snackBarSpy).toHaveBeenCalledWith('Session updated !', 'Close', { duration: 3000 })
    expect(routerSpy).toHaveBeenCalledWith(['sessions']);
  });

});
