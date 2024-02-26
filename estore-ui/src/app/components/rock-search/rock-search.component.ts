import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';
import { RockService } from '../../services/rock/rock.service';
import { Rock } from '../../models/Rock';

@Component({
  selector: 'app-rock-search',
  templateUrl: './rock-search.component.html',
  styleUrls: [ './rock-search.component.css' ]
})
export class RockSearchComponent implements OnInit {
  rocks$!: Observable<Rock[]>;
  private searchTerms = new Subject<string>();

  constructor(private rockService: RockService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.rocks$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.rockService.searchRocks(term)),
    );
  }
}