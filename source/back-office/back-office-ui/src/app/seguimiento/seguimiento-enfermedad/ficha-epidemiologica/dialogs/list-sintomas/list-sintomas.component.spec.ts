import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSintomasComponent } from './list-sintomas.component';

describe('ListSintomasComponent', () => {
  let component: ListSintomasComponent;
  let fixture: ComponentFixture<ListSintomasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListSintomasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSintomasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
