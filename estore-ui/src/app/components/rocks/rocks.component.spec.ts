import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RocksComponent } from './rocks.component';

describe('RocksComponent', () => {
  let component: RocksComponent;
  let fixture: ComponentFixture<RocksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RocksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RocksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
