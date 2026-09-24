export function LoadingState({ text = "Loading…" }) { return <div className="state-card"><div className="spinner"/><p>{text}</p></div>; }
export function ErrorState({ message, onRetry }) { return <div className="state-card"><h3>We couldn’t load this yet</h3><p>{message}</p>{onRetry && <button className="button secondary" onClick={onRetry}>Try again</button>}</div>; }
export function EmptyState({ title, text }) { return <div className="state-card"><h3>{title}</h3><p>{text}</p></div>; }
