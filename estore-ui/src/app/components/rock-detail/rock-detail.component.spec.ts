import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RockDetailComponent } from './rock-detail.component';

describe('RockDetailComponent', () => {
  let component: RockDetailComponent;
  let fixture: ComponentFixture<RockDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RockDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RockDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
