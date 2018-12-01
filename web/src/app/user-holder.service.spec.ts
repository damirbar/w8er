import { TestBed } from '@angular/core/testing';

import { UserHolderService } from './user-holder.service';

describe('UserHolderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserHolderService = TestBed.get(UserHolderService);
    expect(service).toBeTruthy();
  });
});
