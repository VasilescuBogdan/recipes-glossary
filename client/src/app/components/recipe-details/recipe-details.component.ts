import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {MatDivider} from "@angular/material/divider";
import {NgForOf} from "@angular/common";
import {RecipeDetailsDto} from "../../dto/recipe-details.dto";

@Component({
  selector: 'app-components.recipe-details',
  standalone: true,
  imports: [MatDialogClose, MatDialogTitle, MatDialogContent, MatDialogActions, MatButton, MatDivider, NgForOf],
  templateUrl: './recipe-details.component.html',
  styleUrl: './recipe-details.component.css'
})
export class RecipeDetailsComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public recipeDetails: RecipeDetailsDto) {
  }
}
