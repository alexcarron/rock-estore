import { TestBed } from '@angular/core/testing';

import { RockService } from './rock.service';

describe('RockService', () => {
  let service: RockService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
