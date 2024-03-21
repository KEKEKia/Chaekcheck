import { useState } from 'react';
import Card from '../common/card';
import LeftIcon from '../../assets/icons/lefticon';
import RightIcon from '../../assets/icons/righticon';
import RightArrowIcon from '../../assets/icons/rightArrowIcon';
import { SearchResultProps } from '../../interface/profile';
import { useModal } from '../modal/modalClass';
import Modal from '../modal/modal';
import { BooksInfo } from '../../interface/api';
import PredictResult from '../predict_result/predictResult';
import { HistoryDetailApirepository } from '../../repository/business/historyRepository';
import CloseIcon from '../../assets/icons/closeIcon';
import ConfirmContents from '../modal/confirmContents';

function Library({
  onSearchResults,
  currentPage,
  setCurrentPage,
  onDelete,
}: SearchResultProps) {
  // 페이지네이션
  const totalItems = onSearchResults?.length || 0;
  const totalPages = Math.ceil(totalItems / 10);

  const next = () => {
    if (currentPage === totalPages) return;
    setCurrentPage(currentPage + 1);
  };

  const prev = () => {
    if (currentPage === 1) return;
    setCurrentPage(currentPage - 1);
  };

  const getPageNumbers = () => {
    const pageNumbers = [];
    const middlePage = Math.ceil(currentPage / 5) * 5;
    const startPage = Math.max(1, middlePage - 4);
    const endPage = Math.min(totalPages, middlePage);

    for (let i = startPage; i <= endPage; i += 1) {
      pageNumbers.push(i);
    }

    return pageNumbers;
  };

  const startIndex = (currentPage - 1) * 10;
  const endIndex = Math.min(startIndex + 10, totalItems);
  // 변수명 처리
  const getStatusText = (status: string) => {
    switch (status) {
      case 'low':
        return '매입불가';
      case 'medium':
        return '중';
      case 'high':
        return '상';
      case 'best':
        return '최상';
      default:
        return '';
    }
  };
  // 더보기 기능
  const [bookname, setBookname] = useState<string[]>([]);
  const handleReadMore = (bookId: number) => {
    if (!bookname.includes(String(bookId))) {
      setBookname(prevExpanded => [...prevExpanded, String(bookId)]);
    } else {
      setBookname(prevExpanded =>
        prevExpanded.filter(id => id !== String(bookId)),
      );
    }
  };

  // 모달 관련
  const { modalOpen, openModal, closeModal } = useModal();
  const [selectBook, SetSelectBook] = useState<BooksInfo>();
  const [isAlertModalOpen, setIsAlertModalOpen] = useState(false);
  const modalName = 'detail';
  const alertsModal = 'alert';

  const handleContent = async (bookid: number) => {
    openModal(modalName);
    try {
      const data = (await HistoryDetailApirepository(bookid)) as BooksInfo;
      SetSelectBook(data);
    } catch {}
  };

  const handleDelete = (id: number) => {
    onDelete(id);
    closeModal(alertsModal);
    closeModal(modalName);
    setIsAlertModalOpen(false);
  };

  return (
    <Card width="w-3/5" height="min-h-[50vh]">
      <div className="m-3 text-xl font-bold">
        내 서재 ({onSearchResults?.length})
      </div>
      {onSearchResults?.length > 0 ? (
        <div>
          <div className="grid grid-cols-5 gap-2 ">
            {onSearchResults &&
              onSearchResults.slice(startIndex, endIndex).map(book => (
                <div className="m-3" key={book.id}>
                  <div
                    className="relative min-h-[25vh]"
                    role="button"
                    tabIndex={0}
                    onClick={() => {
                      (async () => {
                        await handleContent(book.id);
                      })();
                    }}
                    onKeyPress={event => {
                      if (event.key === 'Enter' || event.key === ' ') {
                        openModal(modalName);
                      }
                    }}>
                    <img
                      src={book.url}
                      alt={book.title}
                      className=" min-h-[25vh]"
                    />
                  </div>
                  <p>
                    책 제목 :{' '}
                    {book.title.length > 10 &&
                    !bookname.includes(String(book.id)) ? (
                      <>
                        {book.title.slice(0, 10)}...
                        <span
                          role="button"
                          tabIndex={0}
                          className="text-BUTTON2-300"
                          onClick={() => handleReadMore(book.id)}
                          onKeyPress={event => {
                            if (event.key === 'Enter' || event.key === ' ') {
                              handleReadMore(book.id);
                            }
                          }}>
                          더 보기
                        </span>
                      </>
                    ) : (
                      book.title
                    )}
                  </p>

                  <p>상태 : {getStatusText(book.status as string)}</p>

                  <p>
                    {' '}
                    {book.price && book.price === '0'
                      ? `가격 : ${book.price}원`
                      : ''}{' '}
                  </p>
                </div>
              ))}
          </div>

          <div className="flex justify-center mt-4">
            <button type="button" onClick={prev} disabled={currentPage === 1}>
              <LeftIcon styleString="w-6 h-6" />
            </button>
            {getPageNumbers().map(pageNumber => (
              <button
                onClick={() => setCurrentPage(pageNumber)}
                className={`mx-2 p-2  ${
                  currentPage === pageNumber
                    ? 'bg-SECONDARY-300 rounded-full w-10'
                    : ''
                }`}
                key={pageNumber}
                type="button">
                {pageNumber}
              </button>
            ))}
            <button
              type="button"
              onClick={next}
              disabled={currentPage === totalPages}>
              <RightIcon styleString="w-6 h-6" />
            </button>
          </div>
        </div>
      ) : (
        <div className="flex h-4/5 justify-center">
          <div className="my-auto">
            <p className="text-3xl">검색 기록이 없어요 😥</p>
            <a
              href="/predict"
              className="flex text-xl mt-3 w-max hover:text-FONT-300">
              <RightArrowIcon styleString="w-6 h-6 mr-2" /> 검색하러가기
            </a>
          </div>
        </div>
      )}

      <Modal
        closeModal={() => !isAlertModalOpen && closeModal(modalName)}
        OpenModal={modalOpen[modalName]}
        width="w-[60%] bg-MAIN-50"
        height="h-full ">
        <CloseIcon
          styleString="absolute right-3 cursor-pointer top-3 w-10 h-10"
          clickMethod={() => closeModal(modalName)}
          index={1}
        />
        {selectBook && (
          <PredictResult
            predictBookInfo={selectBook}
            buttonInfo={{
              height: 'h-[3rem]',
              width: 'w-[10rem]',
              defaultColor: 'bg-BUTTON1-500',
              selectedColor: 'bg-BUTTON1-900',
              fontColor: 'text-FONT-50 text-lg',
              children: '삭제하기',
              action: () => {
                openModal(alertsModal);
                setIsAlertModalOpen(true);
              },
            }}
          />
        )}
      </Modal>

      <Modal
        closeModal={() => {
          closeModal(alertsModal);
          setIsAlertModalOpen(false);
        }}
        OpenModal={modalOpen[alertsModal]}
        width="w-[20%] bg-MAIN-50"
        height="">
        {selectBook && selectBook.bookId && (
          <ConfirmContents
            content="정말로 삭제하시겠습니까?"
            okAction={() => {
              handleDelete(selectBook.bookId);
            }}
            cancelAction={() => {
              closeModal(alertsModal);
              setIsAlertModalOpen(false);
            }}
          />
        )}
      </Modal>
    </Card>
  );
}

export default Library;
