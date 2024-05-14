export interface RecipeDetailsDto {
  description: string;
  cookingTime: number;
  preparationTime: number;
  ingredients: string[];
  collections: string[];
  keywords: string[];
  diets: string;
}
