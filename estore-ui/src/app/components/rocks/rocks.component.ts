import { Component } from '@angular/core';
import { Rock } from '../../models/Rock';
import { RockService } from '../../services/rock/rock.service';
import { MessageService } from '../../services/message/message.service';

@Component({
  selector: 'app-rocks',
  templateUrl: './rocks.component.html',
  styleUrl: './rocks.component.css'
})
export class RocksComponent {
	rocks: Rock[] = [];

	constructor(
		private rockService: RockService,
	) {}

	retrieveRocks(): void {
		this.rockService.getRocks()
			.subscribe(rocks => this.rocks = rocks);
	}

	ngOnInit(): void {
		this.retrieveRocks();
	}

	add(name: string): void {
		name = name.trim();

		if (!name) return;

		const rock = {name};
		this.rockService.addRock(rock as Rock)
			.subscribe(rock => {
				this.rocks.push(rock);
			});
	}

	delete(rock_deleting: Rock): void {
		this.rocks = this.rocks.filter(rock => rock !== rock_deleting);
		this.rockService.deleteRock(rock_deleting.id).subscribe();
	}
}