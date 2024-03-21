import { useState } from 'react';
import Card from '../components/common/card';
import SearchBar from '../components/profile_page/searchBar';
import Library from '../components/profile_page/library';
import { useNickname } from '../data_source/apiInfo';
import { Book } from '../interface/api';
import { HistoryDeleteApirepository } from '../repository/business/historyRepository';

function ProfilePage() {
  const name = useNickname();
  const [search, Setsearch] = useState<Book[]>([]);
  const [currentPage, setCurrentPage] = useState(1);

  const handleSearch = (result: Book[]) => {
    Setsearch(result);
    setCurrentPage(1);
  };

  const handleDelete = (bookid: number) => {
    HistoryDeleteApirepository(bookid);
    const updatedSearchResults = search.filter(book => book.id !== bookid);
    Setsearch(updatedSearchResults);

    if (currentPage > Math.ceil(updatedSearchResults.length / 10)) {
      setCurrentPage(Math.ceil(updatedSearchResults.length / 10));
    }
  };

  return (
    <>
      <Card width="w-3/5" height="min-h-[30vh]">
        <p className="text-4xl font-bold text-center ">{name} 님 안녕하세요!</p>
        <SearchBar onSearchResults={handleSearch} />
      </Card>
      <hr className="mt-5 w-3/5 mx-auto text-FONT-200" />
      <Library
        onSearchResults={search}
        onDelete={handleDelete}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
      />
    </>
  );
}

export default ProfilePage;
