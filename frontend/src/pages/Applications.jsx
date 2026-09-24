import { useCallback, useEffect, useState } from "react";
import AppShell from "../components/AppShell";
import { getMyApplications } from "../services/api";
import { LoadingState, ErrorState, EmptyState } from "../components/AsyncState";
export default function Applications({ user, page, onNavigate, onLogout }) {
  const [items, setItems] = useState([]), [loading, setLoading] = useState(true), [error, setError] = useState("");
  const load = useCallback(async () => { setLoading(true); setError(""); try { setItems(await getMyApplications(user.id)); } catch (e) { setError(e.message); } finally { setLoading(false); } }, [user.id]);
  useEffect(() => { const timer = setTimeout(load, 0); return () => clearTimeout(timer); }, [load]);
  return <AppShell {...{ user, page, onNavigate, onLogout }}><main className="content"><div className="section-head"><div><span className="eyebrow">APPLICATION TRACKER</span><h1>My applications</h1><p>Track every role you have applied to from CareerForge.</p></div><button className="button secondary" onClick={load}>Refresh</button></div>{loading ? <LoadingState text="Loading your applications…" /> : error ? <ErrorState message={error} onRetry={load} /> : !items.length ? <EmptyState title="No applications yet" text="Explore open roles and submit your first application in CareerForge." /> : <div className="applications-list">{items.map(a => <article className="application-card" key={a.id}><div><h3>{a.jobTitle}</h3><p>{a.company} · {a.location}</p></div><div><span className={`status ${a.status.toLowerCase()}`}>{a.status}</span><p>Applied {new Date(a.appliedAt).toLocaleDateString()}</p></div></article>)}</div>}</main></AppShell>;
}
