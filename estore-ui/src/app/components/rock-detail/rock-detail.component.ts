import { Component, Input } from '@angular/core';
import { Rock } from '../../models/Rock';
import { ActivatedRoute } from '@angular/router';
import { RockService } from '../../services/rock/rock.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-rock-detail',
  templateUrl: './rock-detail.component.html',
  styleUrl: './rock-detail.component.css'
})
export class RockDetailComponent {
	@Input() rock?: Rock;

	constructor(
		private route: ActivatedRoute,
		private rockService: RockService,
		private location: Location,
	) {}

	ngOnInit(): void {
		this.retrieveRock();
	}

	retrieveRock(): void {
		const id = Number(this.route.snapshot.paramMap.get('id'));
		this.rockService.getRock(id)
			.subscribe(rock => this.rock = rock);
	}

	goBack(): void {
		this.location.back();
	}

	save(): void {
		if (this.rock) {
			this.rockService.updateRock(this.rock)
				.subscribe(() => this.goBack())
		}
	}
}
