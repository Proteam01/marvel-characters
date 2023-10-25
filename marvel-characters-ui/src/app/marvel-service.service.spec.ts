import { TestBed } from '@angular/core/testing';

import { MarvelService } from './marvel-service.service';

describe('MarvelServiceService', () => {
  let service: MarvelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MarvelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
