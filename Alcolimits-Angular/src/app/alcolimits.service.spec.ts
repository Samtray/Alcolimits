import { TestBed } from '@angular/core/testing';

import { AlcolimitsService } from './alcolimits.service';

describe('AlcolimitsService', () => {
  let service: AlcolimitsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlcolimitsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
