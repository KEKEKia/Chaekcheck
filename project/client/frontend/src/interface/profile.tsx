import { Book } from './api';

export interface SearchProps {
  onSearchResults: (result: Book[]) => void;
}

export interface SearchResultProps {
  currentPage: number;
  setCurrentPage: (newPage: number) => void;
  onSearchResults: Book[];
  onDelete: (bookid: number) => void;
}
