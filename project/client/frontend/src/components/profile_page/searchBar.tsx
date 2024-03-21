import { useEffect, useState, useCallback } from 'react';
import SearchIcon from '../../assets/icons/searchIcon';
import { SearchProps } from '../../interface/profile';
import { HistorySearchApirepository } from '../../repository/business/historyRepository';

function searchBar({ onSearchResults }: SearchProps) {
  const [keyword, setKeyword] = useState<string>('');
  const SearchResults = useCallback(onSearchResults, [onSearchResults]);

  useEffect(() => {
    HistorySearchApirepository(keyword)
      .then(response => {
        if (response && typeof response !== 'string') {
          SearchResults(response);
        }
      })
      .catch(() => {});
  }, [keyword]);

  return (
    <div className="w-3/4 h-1/5 m-auto mt-20">
      <div> 내 서재 검색</div>
      <div className="flex bg-MAIN-100 rounded-2xl p-1 mt-2">
        <SearchIcon styleString="w-6 h-6 ml-2" />
        <input
          className="bg-transparent w-full"
          placeholder="기록을 검색해보세요."
          onChange={event => {
            setKeyword(event.target.value);
          }}
        />
      </div>
    </div>
  );
}

export default searchBar;
