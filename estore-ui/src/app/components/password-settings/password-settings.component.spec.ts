import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordSettingsComponent } from './password-settings.component';

describe('PasswordSettingsComponent', () => {
  let component: PasswordSettingsComponent;
  let fixture: ComponentFixture<PasswordSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PasswordSettingsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PasswordSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
