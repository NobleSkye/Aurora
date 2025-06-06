import { Shield, ArrowLeft } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import Link from "next/link"

export default function EulaPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900">
      {/* Header */}
      <header className="border-b border-white/10 bg-black/20 backdrop-blur-sm">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <div className="w-8 h-8 rounded-lg overflow-hidden">
                <img src="/auerora.png" alt="Aurora" className="w-full h-full object-cover" />
              </div>
              <h1 className="text-2xl font-bold text-white">Aurora</h1>
            </div>
            <Button variant="ghost" className="text-slate-400 hover:text-white" asChild>
              <Link href="/">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Back to Home
              </Link>
            </Button>
          </div>
        </div>
      </header>

      {/* EULA Content */}
      <section className="py-16 px-4">
        <div className="container mx-auto max-w-4xl">
          <div className="text-center mb-12">
            <h1 className="text-4xl md:text-5xl font-bold text-white mb-6 bg-gradient-to-r from-purple-400 via-pink-400 to-purple-400 bg-clip-text text-transparent">
              End User License Agreement
            </h1>
            <p className="text-slate-300 text-lg">
              Please read these terms carefully before using Aurora
            </p>
          </div>

          <Card className="bg-black/40 border-white/10 backdrop-blur-sm">
            <CardHeader>
              <CardTitle className="text-white flex items-center gap-2">
                <Shield className="w-6 h-6 text-red-400" />
                Terms and Conditions
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              <p className="text-slate-300 text-lg">
                By downloading, installing, or using Aurora, you agree to be bound by the following terms and conditions:
              </p>

              <div className="bg-red-500/10 border border-red-500/20 rounded-lg p-6">
                <h3 className="text-red-200 font-bold text-lg mb-3">🚫 Distribution Restrictions</h3>
                <p className="text-red-200 font-medium">
                  <strong>IMPORTANT:</strong> You may NOT share, distribute, or redistribute Aurora in any form, whether
                  modified or unmodified, except by linking to the original source on Modrinth. Direct file sharing
                  or hosting of Aurora files is prohibited.
                </p>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">License Terms</h3>
                <ul className="text-slate-300 space-y-3 list-disc list-inside pl-4">
                  <li>Aurora is provided "as is" during the early beta phase without any warranties, express or implied</li>
                  <li>Use Aurora at your own risk in your Minecraft worlds - always backup your saves before use</li>
                  <li>The developers are not responsible for any damage or loss caused by the use of Aurora</li>
                  <li>Aurora may be used for personal and commercial purposes</li>
                  <li>You may not reverse engineer, decompile, or disassemble Aurora</li>
                  <li>Respect the intellectual property rights of the developers and contributors</li>
                </ul>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">User Responsibilities</h3>
                <ul className="text-slate-300 space-y-3 list-disc list-inside pl-4">
                  <li>Report bugs and issues through our official channels on GitHub or Modrinth</li>
                  <li>Do not use Aurora to grief or harm other players' experiences</li>
                  <li>Follow Minecraft's Terms of Service and your server's rules when using Aurora</li>
                  <li>Keep Aurora updated to the latest version for optimal performance and security</li>
                </ul>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">Beta Software Notice</h3>
                <div className="bg-yellow-500/10 border border-yellow-500/20 rounded-lg p-4">
                  <p className="text-yellow-200">
                    Aurora is currently in early beta. Features may be incomplete, unstable, or subject to change. 
                    Your feedback and bug reports help us improve the software for everyone.
                  </p>
                </div>
              </div>

              <div className="space-y-4">
                <h3 className="text-white font-bold text-xl">Changes to Terms</h3>
                <p className="text-slate-300">
                  We reserve the right to modify these terms at any time. Continued use of Aurora after changes 
                  constitutes acceptance of the new terms. We will notify users of significant changes through 
                  our official channels.
                </p>
              </div>

              <div className="pt-6 border-t border-white/10">
                <p className="text-slate-400 text-sm">
                  Last updated: June 5, 2025
                </p>
              </div>
            </CardContent>
          </Card>

          <div className="text-center mt-8">
            <Button
              size="lg"
              className="bg-gradient-to-r from-purple-600 to-pink-600 hover:from-purple-700 hover:to-pink-700 text-white"
              asChild
            >
              <Link href="/">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Return to Home
              </Link>
            </Button>
          </div>
        </div>
      </section>
    </div>
  )
}
