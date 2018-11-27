import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestRequestsComponent } from './rest-requests.component';

describe('RestRequestsComponent', () => {
  let component: RestRequestsComponent;
  let fixture: ComponentFixture<RestRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RestRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
