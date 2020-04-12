import { TestBed } from '@angular/core/testing';

import { SeguimientoService } from './seguimiento.service';

describe('SeguimientoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SeguimientoService = TestBed.get(SeguimientoService);
    expect(service).toBeTruthy();
  });
});
