function LoadingBook() {
  return (
    // <div className="loading-books w-[200px] h-[140px] bg-[#979794] border-4 relative rounded-lg perspective before:content-[''] before:absolute before:left-[10px] before:right-[10px] before:top-[10px] before:bottom-[10px] before:rounded-lg before:bg-[#f5f5f5] before:bg-no-repeat  before:bg-[length:60px_10px] before:bg-[linear-gradient(#ddd 100px) left_top_15px_30px] after:content-[''] after:absolute after:w-[calc(theme('top.4')*-1)]" />
    <div className="w-[2rem] h-[2rem] animate-spin rounded-md bg-MAIN-400 m-auto" />
  );
}
export default LoadingBook;
