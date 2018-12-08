import { TestBed } from '@angular/core/testing';

import { UserUpdatesService } from './user-updates.service';

describe('UserUpdatesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserUpdatesService = TestBed.get(UserUpdatesService);
    expect(service).toBeTruthy();
  });
});
