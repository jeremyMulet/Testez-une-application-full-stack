import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import {fireEvent, render, screen} from "@testing-library/angular";
import {of, throwError} from "rxjs";
import {AuthService} from "../../services/auth.service";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  test('should render the registration form', async () => {
    await render(RegisterComponent, {
      imports: [ReactiveFormsModule],
      // provide any required dependencies here...
    });

    expect(screen.getByPlaceholderText('First name')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Last name')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Email')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument();
    expect(screen.getByRole('button', {name: /submit/i})).toBeInTheDocument();
  });

  test('should disable submit button if form is invalid', async () => {
    const { getByRole } = await render(RegisterComponent, {
      imports: [ReactiveFormsModule],
    });

    const submitButton = getByRole('button', { name: /submit/i });
    expect(submitButton).toBeDisabled();
  });

  test('should submit the form when valid data is provided', async () => {
    const mockAuthService = {
      register: jest.fn().mockReturnValue(of(null)),
    };

    const { getByPlaceholderText, getByRole } = await render(RegisterComponent, {
      imports: [ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        // Provide other dependencies if required...
      ],
    });

    fireEvent.input(getByPlaceholderText('First name'), { target: { value: 'John' } });
    fireEvent.input(getByPlaceholderText('Last name'), { target: { value: 'Doe' } });
    fireEvent.input(getByPlaceholderText('Email'), { target: { value: 'john.doe@example.com' } });
    fireEvent.input(getByPlaceholderText('Password'), { target: { value: 'password123' } });

    fireEvent.click(getByRole('button', { name: /submit/i }));
    expect(mockAuthService.register).toHaveBeenCalled();
  });

  test('should display an error message when the API call fails', async () => {
    const mockAuthService = {
      register: jest.fn().mockReturnValue(throwError('API error')),
    };

    const { getByPlaceholderText, getByRole } = await render(RegisterComponent, {
      imports: [ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
      ],
    });

    fireEvent.input(getByPlaceholderText('First name'), { target: { value: 'John' } });
    fireEvent.input(getByPlaceholderText('Last name'), { target: { value: 'Doe' } });
    fireEvent.input(getByPlaceholderText('Email'), { target: { value: 'john.doe@example.com' } });
    fireEvent.input(getByPlaceholderText('Password'), { target: { value: 'password123' } });

    fireEvent.click(getByRole('button', { name: /submit/i }));
    expect(screen.getByText(/an error occurred/i)).toBeInTheDocument();
  });
});

describe('RegisterComponent', () => {

});
