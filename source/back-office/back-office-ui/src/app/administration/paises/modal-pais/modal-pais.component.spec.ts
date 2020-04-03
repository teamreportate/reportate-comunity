import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPaisComponent } from './modal-pais.component';

describe('ModalPaisComponent', () => {
  let component: ModalPaisComponent;
  let fixture: ComponentFixture<ModalPaisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalPaisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalPaisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
